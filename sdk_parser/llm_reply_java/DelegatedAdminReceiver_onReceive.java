import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.app.admin.DelegatedAdminReceiver;
import android.net.Uri;
import android.security.keystore.ParcelableX500Principal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DelegatedAdminReceiver_onReceive {

    public void test_DelegatedAdminReceiver_onReceive() {
    
           Context context = this;
    
           DelegatedAdminReceiver delegatedAdminReceiver = new DelegatedAdminReceiver() {
               @Override
               public void onReceive(Context context, Intent intent) {
                   super.onReceive(context, intent);
               }
    
               @Override
               public String onChoosePrivateKeyAlias(Context context, Intent intent, int uid, Uri uri, String alias) {
                   return super.onChoosePrivateKeyAlias(context, intent, uid, uri, alias);
               }
    
               @Override
               public List<String> onChoosePrivateKeyOids(Context context, Intent intent, int uid, Uri uri) {
                   return super.onChoosePrivateKeyOids(context, intent, uid, uri);
               }
    
               @Override
               public byte[] onDecrypt(Context context, Intent intent, byte[] data) {
                   return super.onDecrypt(context, intent, data);
               }
    
               @Override
               public byte[] onEncrypt(Context context, Intent intent, byte[] data) {
                   return super.onEncrypt(context, intent, data);
               }
    
    
               @Override
               public CharSequence onGenerateCertificateRequest(Context context, Intent intent, int uid, String alias, ParcelableX500Principal subjectDN, BigInteger serialNumber, Date notBefore, Date notAfter, List<String> flags) {
                   return super.onGenerateCertificateRequest(context, intent, uid, alias, subjectDN, serialNumber, notBefore, notAfter, flags);
               }
    
           };
    
           Intent intent = new Intent(DevicePolicyManager.ACTION_DEVICE_ADMIN_DISABLE_REQUESTED);
           intent.setComponent(new ComponentName(context, DelegatedAdminReceiver.class));
           delegatedAdminReceiver.onReceive(context, intent);
    
    }

}