package widgets;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Project naspgui.
 * Created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class Job {
    private StringProperty jobId = new SimpleStringProperty();
    private StringProperty runName = new SimpleStringProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty queue = new SimpleStringProperty();
    private StringProperty jobName = new SimpleStringProperty();
    private StringProperty sessionId = new SimpleStringProperty();
    private StringProperty nds = new SimpleStringProperty();
    private StringProperty reqTask = new SimpleStringProperty();
    private StringProperty reqMem = new SimpleStringProperty();
    private StringProperty elapsedTime = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();

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

    public Job( String runName, String jobId, String userName, String queue, String jobName, String sessionId,
                String nds, String reqTask, String reqMem, String elapsedTime, String time,
                String status){

        this.runName.setValue( runName );
        this.jobId.setValue( jobId );
        this.userName.setValue( userName );
        this.queue.setValue( queue );
        this.jobName.setValue( jobName );
        this.sessionId.setValue( sessionId );
        this.nds.setValue( nds );
        this.reqTask.setValue( reqTask );
        this.reqMem.setValue( reqMem );
        this.elapsedTime.setValue( elapsedTime );
        this.time.setValue( time );
        this.status.setValue( status );
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
