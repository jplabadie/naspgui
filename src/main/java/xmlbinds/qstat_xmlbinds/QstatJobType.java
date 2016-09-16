
package xmlbinds.qstat_xmlbinds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JobType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Job_Id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Job_Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Job_Owner">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="callender@tnorth-login"/>
 *               &lt;enumeration value="dlemmer@tnorth-login"/>
 *               &lt;enumeration value="ksheridan@tnorth-login"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="resources_used" type="{}resources_usedType" minOccurs="0"/>
 *         &lt;element name="job_state">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="H"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="R"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="queue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="server" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Checkpoint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ctime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="depend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Error_Path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exec_host" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exec_port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hold_Types">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="n"/>
 *               &lt;enumeration value="s"/>
 *               &lt;enumeration value="u"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Join_Path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Keep_Files" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mail_Points">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="a"/>
 *               &lt;enumeration value="e"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="mtime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Output_Path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qtime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Rerunable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Resource_List" type="{}Resource_ListType"/>
 *         &lt;element name="session_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="etime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exit_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="submit_args" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="start_time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Walltime" type="{}WalltimeType" minOccurs="0"/>
 *         &lt;element name="start_count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fault_tolerant" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comp_time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="submit_host" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="init_work_dir" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QstatJobType", propOrder = {
    "jobId",
    "jobName",
    "jobOwner",
    "resourcesUsed",
    "jobState",
    "queue",
    "server",
    "checkpoint",
    "ctime",
    "depend",
    "errorPath",
    "execHost",
    "execPort",
    "holdTypes",
    "joinPath",
    "keepFiles",
    "mailPoints",
    "mtime",
    "outputPath",
    "priority",
    "qtime",
    "rerunable",
    "resourceList",
    "sessionId",
    "etime",
    "exitStatus",
    "submitArgs",
    "startTime",
    "walltime",
    "startCount",
    "faultTolerant",
    "compTime",
    "submitHost",
    "initWorkDir"
})
public class QstatJobType {

    @XmlElement(name = "Job_Id", required = true)
    protected String jobId;
    @XmlElement(name = "Job_Name", required = true)
    protected String jobName;
    @XmlElement(name = "Job_Owner", required = true)
    protected String jobOwner;
    @XmlElement(name = "resources_used")
    protected QstatResourcesUsedType resourcesUsed;
    @XmlElement(name = "job_state", required = true)
    protected String jobState;
    @XmlElement(required = true)
    protected String queue;
    @XmlElement(required = true)
    protected String server;
    @XmlElement(name = "Checkpoint", required = true)
    protected String checkpoint;
    @XmlElement(required = true)
    protected String ctime;
    protected String depend;
    @XmlElement(name = "Error_Path", required = true)
    protected String errorPath;
    @XmlElement(name = "exec_host")
    protected String execHost;
    @XmlElement(name = "exec_port")
    protected String execPort;
    @XmlElement(name = "Hold_Types", required = true)
    protected String holdTypes;
    @XmlElement(name = "Join_Path", required = true)
    protected String joinPath;
    @XmlElement(name = "Keep_Files", required = true)
    protected String keepFiles;
    @XmlElement(name = "Mail_Points", required = true)
    protected String mailPoints;
    @XmlElement(required = true)
    protected String mtime;
    @XmlElement(name = "Output_Path", required = true)
    protected String outputPath;
    @XmlElement(name = "Priority", required = true)
    protected String priority;
    @XmlElement(required = true)
    protected String qtime;
    @XmlElement(name = "Rerunable", required = true)
    protected String rerunable;
    @XmlElement(name = "Resource_List", required = true)
    protected QstatResourceListType resourceList;
    @XmlElement(name = "session_id")
    protected String sessionId;
    protected String etime;
    @XmlElement(name = "exit_status")
    protected String exitStatus;
    @XmlElement(name = "submit_args", required = true)
    protected String submitArgs;
    @XmlElement(name = "start_time")
    protected String startTime;
    @XmlElement(name = "Walltime")
    protected QstatWalltimeType walltime;
    @XmlElement(name = "start_count")
    protected String startCount;
    @XmlElement(name = "fault_tolerant", required = true)
    protected String faultTolerant;
    @XmlElement(name = "comp_time")
    protected String compTime;
    @XmlElement(name = "submit_host", required = true)
    protected String submitHost;
    @XmlElement(name = "init_work_dir", required = true)
    protected String initWorkDir;

    /**
     * Gets the value of the jobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the value of the jobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobId(String value) {
        this.jobId = value;
    }

    /**
     * Gets the value of the jobName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets the value of the jobName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobName(String value) {
        this.jobName = value;
    }

    /**
     * Gets the value of the jobOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobOwner() {
        return jobOwner;
    }

    /**
     * Sets the value of the jobOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobOwner(String value) {
        this.jobOwner = value;
    }

    /**
     * Gets the value of the resourcesUsed property.
     * 
     * @return
     *     possible object is
     *     {@link QstatResourcesUsedType }
     *     
     */
    public QstatResourcesUsedType getResourcesUsed() {
        return resourcesUsed;
    }

    /**
     * Sets the value of the resourcesUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link QstatResourcesUsedType }
     *     
     */
    public void setResourcesUsed(QstatResourcesUsedType value) {
        this.resourcesUsed = value;
    }

    /**
     * Gets the value of the jobState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobState() {
        return jobState;
    }

    /**
     * Sets the value of the jobState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobState(String value) {
        this.jobState = value;
    }

    /**
     * Gets the value of the queue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueue() {
        return queue;
    }

    /**
     * Sets the value of the queue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueue(String value) {
        this.queue = value;
    }

    /**
     * Gets the value of the server property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the value of the server property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServer(String value) {
        this.server = value;
    }

    /**
     * Gets the value of the checkpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckpoint() {
        return checkpoint;
    }

    /**
     * Sets the value of the checkpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckpoint(String value) {
        this.checkpoint = value;
    }

    /**
     * Gets the value of the ctime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtime() {
        return ctime;
    }

    /**
     * Sets the value of the ctime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtime(String value) {
        this.ctime = value;
    }

    /**
     * Gets the value of the depend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepend() {
        return depend;
    }

    /**
     * Sets the value of the depend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepend(String value) {
        this.depend = value;
    }

    /**
     * Gets the value of the errorPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorPath() {
        return errorPath;
    }

    /**
     * Sets the value of the errorPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorPath(String value) {
        this.errorPath = value;
    }

    /**
     * Gets the value of the execHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecHost() {
        return execHost;
    }

    /**
     * Sets the value of the execHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecHost(String value) {
        this.execHost = value;
    }

    /**
     * Gets the value of the execPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecPort() {
        return execPort;
    }

    /**
     * Sets the value of the execPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecPort(String value) {
        this.execPort = value;
    }

    /**
     * Gets the value of the holdTypes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoldTypes() {
        return holdTypes;
    }

    /**
     * Sets the value of the holdTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoldTypes(String value) {
        this.holdTypes = value;
    }

    /**
     * Gets the value of the joinPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinPath() {
        return joinPath;
    }

    /**
     * Sets the value of the joinPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinPath(String value) {
        this.joinPath = value;
    }

    /**
     * Gets the value of the keepFiles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeepFiles() {
        return keepFiles;
    }

    /**
     * Sets the value of the keepFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeepFiles(String value) {
        this.keepFiles = value;
    }

    /**
     * Gets the value of the mailPoints property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailPoints() {
        return mailPoints;
    }

    /**
     * Sets the value of the mailPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailPoints(String value) {
        this.mailPoints = value;
    }

    /**
     * Gets the value of the mtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtime() {
        return mtime;
    }

    /**
     * Sets the value of the mtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMtime(String value) {
        this.mtime = value;
    }

    /**
     * Gets the value of the outputPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * Sets the value of the outputPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputPath(String value) {
        this.outputPath = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the qtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQtime() {
        return qtime;
    }

    /**
     * Sets the value of the qtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQtime(String value) {
        this.qtime = value;
    }

    /**
     * Gets the value of the rerunable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRerunable() {
        return rerunable;
    }

    /**
     * Sets the value of the rerunable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRerunable(String value) {
        this.rerunable = value;
    }

    /**
     * Gets the value of the resourceList property.
     * 
     * @return
     *     possible object is
     *     {@link QstatResourceListType }
     *     
     */
    public QstatResourceListType getResourceList() {
        return resourceList;
    }

    /**
     * Sets the value of the resourceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link QstatResourceListType }
     *     
     */
    public void setResourceList(QstatResourceListType value) {
        this.resourceList = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the etime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEtime() {
        return etime;
    }

    /**
     * Sets the value of the etime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEtime(String value) {
        this.etime = value;
    }

    /**
     * Gets the value of the exitStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExitStatus() {
        return exitStatus;
    }

    /**
     * Sets the value of the exitStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExitStatus(String value) {
        this.exitStatus = value;
    }

    /**
     * Gets the value of the submitArgs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitArgs() {
        return submitArgs;
    }

    /**
     * Sets the value of the submitArgs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitArgs(String value) {
        this.submitArgs = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the walltime property.
     * 
     * @return
     *     possible object is
     *     {@link QstatWalltimeType }
     *     
     */
    public QstatWalltimeType getWalltime() {
        return walltime;
    }

    /**
     * Sets the value of the walltime property.
     * 
     * @param value
     *     allowed object is
     *     {@link QstatWalltimeType }
     *     
     */
    public void setWalltime(QstatWalltimeType value) {
        this.walltime = value;
    }

    /**
     * Gets the value of the startCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartCount() {
        return startCount;
    }

    /**
     * Sets the value of the startCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartCount(String value) {
        this.startCount = value;
    }

    /**
     * Gets the value of the faultTolerant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultTolerant() {
        return faultTolerant;
    }

    /**
     * Sets the value of the faultTolerant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultTolerant(String value) {
        this.faultTolerant = value;
    }

    /**
     * Gets the value of the compTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompTime() {
        return compTime;
    }

    /**
     * Sets the value of the compTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompTime(String value) {
        this.compTime = value;
    }

    /**
     * Gets the value of the submitHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitHost() {
        return submitHost;
    }

    /**
     * Sets the value of the submitHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitHost(String value) {
        this.submitHost = value;
    }

    /**
     * Gets the value of the initWorkDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitWorkDir() {
        return initWorkDir;
    }

    /**
     * Sets the value of the initWorkDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitWorkDir(String value) {
        this.initWorkDir = value;
    }

}
