"""
RQ2实验脚本：从已有预测文件中采样1000个API，使用auto模式预测，对比ground truth
"""

import json
import os
import random
import sys
from typing import Dict, List

# Add current directory to path
sys.path.append(os.path.dirname(os.path.abspath(__file__)))


def load_apis_from_prediction_file(prediction_file: str) -> List[Dict]:
    """
    从预测文件中加载所有API
    格式: 文件路径,方法名,预测结果(Yes/No)
    """
    print(f"从预测文件加载API: {prediction_file}")
    apis = []
    
    with open(prediction_file, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if not line:
                continue
            
            parts = line.split(',', 2)  # 最多分割成3部分
            if len(parts) >= 2:
                java_file_path = parts[0].strip()
                method_name = parts[1].strip()
                prediction = parts[2].strip() if len(parts) > 2 else 'No'
                
                apis.append({
                    'java_file_path': java_file_path,
                    'method_name': method_name,
                    'existing_prediction': prediction
                })
    
    print(f"加载了 {len(apis)} 个API")
    return apis


def sample_1000_apis(apis: List[Dict], seed: int = 42, output_file: str = "sampled_1000_apis.json"):
    """
    步骤1: 随机采样1000个API
    """
    print("\n" + "="*60)
    print("步骤1: 随机采样1000个API")
    print("="*60)
    
    # 如果已经存在采样结果，直接加载
    if os.path.exists(output_file):
        print(f"发现已存在的采样结果: {output_file}")
        with open(output_file, 'r', encoding='utf-8') as f:
            sampled = json.load(f)
        print(f"加载了 {len(sampled)} 个采样API")
        return sampled
    
    print(f"从 {len(apis)} 个API中随机采样1000个...")
    random.seed(seed)
    sampled = random.sample(apis, min(1000, len(apis)))
    
    # 保存采样结果
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(sampled, f, indent=2, ensure_ascii=False)
    
    print(f"采样完成: {len(sampled)} 个API")
    print(f"采样结果已保存到: {output_file}")
    return sampled


def predict_with_auto(sampled_apis: List[Dict], output_file: str = "predictions.json"):
    """
    步骤2: 使用auto模式（Full Pipeline）预测每个API是否需要permission
    """
    print("\n" + "="*60)
    print("步骤2: 使用auto模式预测permission需求")
    print("="*60)
    
    # 如果已经存在预测结果，直接加载
    if os.path.exists(output_file):
        print(f"发现已存在的预测结果: {output_file}")
        with open(output_file, 'r', encoding='utf-8') as f:
            predictions = json.load(f)
        print(f"加载了 {len(predictions)} 个预测结果")
        return predictions
    
    # 导入LLM相关模块
    try:
        from llm_parser import advanced_dual_role_permission_check
    except ImportError as e:
        print(f"错误: 无法导入LLM模块: {e}")
        print("将使用已有预测结果")
        predictions = []
        for api_data in sampled_apis:
            predictions.append({
                'java_file_path': api_data['java_file_path'],
                'method_name': api_data['method_name'],
                'predicted_requires_permission': 'Yes' in api_data.get('existing_prediction', 'No'),
                'used_existing_prediction': True,
                'final_decision': api_data.get('existing_prediction', 'No')
            })
        
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(predictions, f, indent=2, ensure_ascii=False)
        return predictions
    
    predictions = []
    
    for i, api_data in enumerate(sampled_apis):
        if i % 10 == 0:
            print(f"预测进度: {i+1}/{len(sampled_apis)} - {api_data['method_name']}")
        
        try:
            # 尝试从文件读取方法代码
            java_file_path = api_data['java_file_path']
            
            # 处理相对路径
            if not os.path.isabs(java_file_path):
                # 尝试多个可能的路径
                possible_paths = [
                    java_file_path,
                    os.path.join("..", java_file_path),
                    os.path.join("../..", java_file_path),
                    os.path.join("../../..", java_file_path),
                ]
                
                for path in possible_paths:
                    if os.path.exists(path):
                        java_file_path = path
                        break
                else:
                    # 如果找不到文件，使用已有预测
                    predictions.append({
                        'java_file_path': api_data['java_file_path'],
                        'method_name': api_data['method_name'],
                        'predicted_requires_permission': 'Yes' in api_data.get('existing_prediction', 'No'),
                        'used_existing_prediction': True,
                        'final_decision': api_data.get('existing_prediction', 'No'),
                        'error': 'File not found, used existing prediction'
                    })
                    continue
            
            # 读取Java文件并提取方法信息
            from java_parser import extract_java_method_info_from_file
            
            methods_info = extract_java_method_info_from_file(java_file_path)
            method_info = None
            
            # 找到对应的方法
            for method in methods_info:
                if method['method_name'] == api_data['method_name']:
                    method_info = method
                    break
            
            if method_info is None:
                # 如果找不到方法，使用已有预测
                predictions.append({
                    'java_file_path': api_data['java_file_path'],
                    'method_name': api_data['method_name'],
                    'predicted_requires_permission': 'Yes' in api_data.get('existing_prediction', 'No'),
                    'used_existing_prediction': True,
                    'final_decision': api_data.get('existing_prediction', 'No'),
                    'error': 'Method not found, used existing prediction'
                })
                continue
            
            method_code = str(method_info)
            
            # 使用advanced_dual_role_permission_check（Full Pipeline的核心）
            result = advanced_dual_role_permission_check(method_code)
            
            prediction = {
                'java_file_path': api_data['java_file_path'],
                'method_name': api_data['method_name'],
                'predicted_requires_permission': result['final_decision'] and 'Yes' in result['final_decision'],
                'detector_response': result.get('detector_response', ''),
                'analyst_response': result.get('analyst_response', ''),
                'final_decision': result.get('final_decision', ''),
                'confidence': result.get('confidence', ''),
                'reasoning': result.get('reasoning', ''),
                'used_existing_prediction': False
            }
            
            predictions.append(prediction)
            
        except Exception as e:
            print(f"预测 {api_data['method_name']} 时出错: {e}")
            # 出错时使用已有预测
            predictions.append({
                'java_file_path': api_data['java_file_path'],
                'method_name': api_data['method_name'],
                'predicted_requires_permission': 'Yes' in api_data.get('existing_prediction', 'No'),
                'used_existing_prediction': True,
                'final_decision': api_data.get('existing_prediction', 'No'),
                'error': str(e)
            })
    
    # 保存预测结果
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(predictions, f, indent=2, ensure_ascii=False)
    
    # 统计
    positive_count = sum(1 for p in predictions if p.get('predicted_requires_permission', False))
    existing_count = sum(1 for p in predictions if p.get('used_existing_prediction', False))
    
    print(f"\n预测完成:")
    print(f"  总API数: {len(predictions)}")
    print(f"  预测需要permission: {positive_count}")
    print(f"  预测不需要permission: {len(predictions) - positive_count}")
    print(f"  使用已有预测: {existing_count}")
    print(f"  重新预测: {len(predictions) - existing_count}")
    print(f"预测结果已保存到: {output_file}")
    
    return predictions


def load_ground_truth(ground_truth_file: str) -> Dict:
    """
    加载ground truth
    ground truth格式: { "java_file_path.method_name": True/False, ... }
    或者从permission_api.txt加载（只包含Yes的）
    """
    if not os.path.exists(ground_truth_file):
        print(f"警告: Ground truth文件不存在: {ground_truth_file}")
        return {}
    
    print(f"加载ground truth: {ground_truth_file}")
    ground_truth = {}
    
    # 检查文件格式
    with open(ground_truth_file, 'r', encoding='utf-8') as f:
        first_line = f.readline().strip()
        f.seek(0)
        
        # 如果是JSON格式
        if first_line.startswith('{'):
            ground_truth = json.load(f)
        else:
            # 如果是文本格式（permission_api.txt格式）
            for line in f:
                line = line.strip()
                if not line:
                    continue
                
                parts = line.split(',', 2)
                if len(parts) >= 2:
                    java_file_path = parts[0].strip()
                    method_name = parts[1].strip()
                    result = parts[2].strip() if len(parts) > 2 else 'No'
                    
                    api_key = f"{java_file_path}.{method_name}"
                    ground_truth[api_key] = 'Yes' in result
    
    print(f"加载了 {len(ground_truth)} 个ground truth条目")
    return ground_truth


def compare_with_ground_truth(predictions: List[Dict], ground_truth: Dict, output_file: str = "comparison_results.json"):
    """
    步骤3: 对比预测结果和ground truth
    """
    print("\n" + "="*60)
    print("步骤3: 对比预测结果和ground truth")
    print("="*60)
    
    # 统计指标
    tp = 0  # True Positive: 预测Yes, ground truth Yes
    fp = 0  # False Positive: 预测Yes, ground truth No
    tn = 0  # True Negative: 预测No, ground truth No
    fn = 0  # False Negative: 预测No, ground truth Yes
    no_gt = 0  # 没有ground truth的API
    
    comparison_results = []
    
    for prediction in predictions:
        api_key = f"{prediction['java_file_path']}.{prediction['method_name']}"
        predicted = prediction.get('predicted_requires_permission', False)
        actual = ground_truth.get(api_key, None)
        
        if actual is None:
            no_gt += 1
            comparison_results.append({
                **prediction,
                'ground_truth': None,
                'match': None,
                'error_type': 'no_ground_truth'
            })
            continue
        
        # 判断匹配情况
        if predicted and actual:
            tp += 1
            match = True
            error_type = None
        elif predicted and not actual:
            fp += 1
            match = False
            error_type = 'false_positive'
        elif not predicted and not actual:
            tn += 1
            match = True
            error_type = None
        elif not predicted and actual:
            fn += 1
            match = False
            error_type = 'false_negative'
        
        comparison_results.append({
            **prediction,
            'ground_truth': actual,
            'match': match,
            'error_type': error_type
        })
    
    # 计算指标
    precision = tp / (tp + fp) if (tp + fp) > 0 else 0.0
    recall = tp / (tp + fn) if (tp + fn) > 0 else 0.0
    f1_score = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0.0
    accuracy = (tp + tn) / (tp + fp + tn + fn) if (tp + fp + tn + fn) > 0 else 0.0
    
    # 保存对比结果
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump({
            'summary': {
                'total_apis': len(predictions),
                'apis_with_ground_truth': tp + fp + tn + fn,
                'apis_without_ground_truth': no_gt,
                'true_positives': tp,
                'false_positives': fp,
                'true_negatives': tn,
                'false_negatives': fn,
                'precision': precision,
                'recall': recall,
                'f1_score': f1_score,
                'accuracy': accuracy
            },
            'detailed_results': comparison_results
        }, f, indent=2, ensure_ascii=False)
    
    # 打印结果
    print(f"\n对比结果:")
    print(f"  总API数: {len(predictions)}")
    print(f"  有ground truth的API: {tp + fp + tn + fn}")
    print(f"  没有ground truth的API: {no_gt}")
    print(f"\n混淆矩阵:")
    print(f"  True Positive (TP):  {tp}")
    print(f"  False Positive (FP): {fp}")
    print(f"  True Negative (TN):  {tn}")
    print(f"  False Negative (FN): {fn}")
    print(f"\n性能指标:")
    print(f"  Precision: {precision:.2%}")
    print(f"  Recall:    {recall:.2%}")
    print(f"  F1-Score:  {f1_score:.2%}")
    print(f"  Accuracy:  {accuracy:.2%}")
    print(f"\n对比结果已保存到: {output_file}")
    
    return {
        'summary': {
            'total_apis': len(predictions),
            'apis_with_ground_truth': tp + fp + tn + fn,
            'apis_without_ground_truth': no_gt,
            'true_positives': tp,
            'false_positives': fp,
            'true_negatives': tn,
            'false_negatives': fn,
            'precision': precision,
            'recall': recall,
            'f1_score': f1_score,
            'accuracy': accuracy
        },
        'detailed_results': comparison_results
    }


def main():
    """主函数"""
    print("="*60)
    print("RQ2实验: 从预测文件采样1000个API并使用auto模式预测")
    print("="*60)
    
    # 配置路径
    # 使用已有的预测文件
    prediction_file = "../permission_prediction_api.txt"  # 所有API的预测结果
    ground_truth_file = "../permission_api.txt"  # Ground truth（只包含Yes的）
    
    # 检查文件是否存在
    if not os.path.exists(prediction_file):
        # 尝试其他路径
        possible_paths = [
            "../permission_prediction_api.txt",
            "../../permission_prediction_api.txt",
            "permission_prediction_api.txt"
        ]
        for path in possible_paths:
            if os.path.exists(path):
                prediction_file = path
                break
        else:
            print(f"错误: 找不到预测文件")
            print(f"请确保存在 permission_prediction_api.txt 文件")
            return
    
    if not os.path.exists(ground_truth_file):
        # 尝试其他路径
        possible_paths = [
            "../permission_api.txt",
            "../../permission_api.txt",
            "permission_api.txt"
        ]
        for path in possible_paths:
            if os.path.exists(path):
                ground_truth_file = path
                break
    
    print(f"使用预测文件: {prediction_file}")
    if os.path.exists(ground_truth_file):
        print(f"使用ground truth文件: {ground_truth_file}")
    else:
        print(f"警告: 未找到ground truth文件，将无法进行对比")
    
    # 输出文件
    output_dir = "rq2_experiment"
    os.makedirs(output_dir, exist_ok=True)
    
    sampled_file = os.path.join(output_dir, "sampled_1000_apis.json")
    predictions_file = os.path.join(output_dir, "predictions.json")
    comparison_file = os.path.join(output_dir, "comparison_results.json")
    
    # 步骤1: 从预测文件加载所有API
    all_apis = load_apis_from_prediction_file(prediction_file)
    
    # 步骤2: 采样1000个API
    sampled_apis = sample_1000_apis(all_apis, seed=42, output_file=sampled_file)
    
    # 步骤3: 使用auto模式预测
    predictions = predict_with_auto(sampled_apis, output_file=predictions_file)
    
    # 步骤4: 加载ground truth并对比
    if os.path.exists(ground_truth_file):
        ground_truth = load_ground_truth(ground_truth_file)
        
        if ground_truth:
            comparison_results = compare_with_ground_truth(predictions, ground_truth, comparison_file)
        else:
            print("\n" + "="*60)
            print("注意: Ground truth文件为空，无法进行对比")
            print("="*60)
    else:
        print("\n" + "="*60)
        print("注意: 没有ground truth文件，无法进行对比")
        print("="*60)
        print("预测结果已保存，请提供ground truth文件后重新运行对比步骤")
        print(f"Ground truth文件格式应为: 文件路径,方法名,Yes/No")
    
    print("\n" + "="*60)
    print("实验完成!")
    print("="*60)


if __name__ == "__main__":
    main()
