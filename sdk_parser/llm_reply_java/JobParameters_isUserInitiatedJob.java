import android.app.job.JobParameters;
import android.util.Log;

public class JobParameters_isUserInitiatedJob {
    public void test_JobParameters_isUserInitiatedJob(JobParameters params) {
        boolean isUserInitiatedJob = params.isOverrideDeadlineExpired();
        
        if(isUserInitiatedJob){
            Log.d("JobParametersTest", "Job was initiated by user");
        }
        else{
            Log.d("JobParametersTest", "Job wasn't initiated by user");
        }
    }
}