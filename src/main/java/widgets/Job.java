package widgets;

import javafx.beans.property.StringProperty;

/**
 * Project naspgui.
 * Created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class Job {
    private StringProperty jobId;
    private StringProperty runName;
    private StringProperty userName;
    private StringProperty queue;
    private StringProperty jobName;
    private StringProperty sessionId;
    private StringProperty nds;
    private StringProperty reqTask;
    private StringProperty reqMem;
    private StringProperty elapsedTime;
    private StringProperty time;
    private StringProperty status;

    public Job(){
        runName.set("");
        jobId.setValue("");
        jobName.setValue("");
        userName.setValue("");
        queue.setValue("");
        sessionId.setValue("");
        nds.setValue("");
        reqTask.setValue("");
        reqMem.setValue("");
        elapsedTime.setValue("");
        time.setValue("");
        status.setValue("");
    }

    public String getJobId() {return jobId.get();}

    public StringProperty jobIdProperty() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId.set(jobId);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getRunName() {
        return runName.get();
    }

    public StringProperty runNameProperty() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName.set(runName);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getElapsedTime() {
        return elapsedTime.get();
    }

    public StringProperty elapsedTimeProperty() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime.set(elapsedTime);
    }

    public String getReqMem() {
        return reqMem.get();
    }

    public StringProperty reqMemProperty() {
        return reqMem;
    }

    public void setReqMem(String reqMem) {
        this.reqMem.set(reqMem);
    }

    public String getNds() {
        return nds.get();
    }

    public StringProperty ndsProperty() {
        return nds;
    }

    public void setNds(String nds) {
        this.nds.set(nds);
    }

    public String getSessionId() {
        return sessionId.get();
    }

    public StringProperty sessionIdProperty() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId.set(sessionId);
    }

    public String getQueue() {
        return queue.get();
    }

    public StringProperty queueProperty() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue.set(queue);
    }

    public String getReqTask() {
        return reqTask.get();
    }

    public StringProperty reqTaskProperty() {
        return reqTask;
    }

    public void setReqTask(String reqTask) {
        this.reqTask.set(reqTask);
    }

    public String getJobName() {return jobName.get();}

    public StringProperty jobNameProperty() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName.set(jobName);
    }


}
