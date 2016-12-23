

/**
 * Created by user on 3/7/2016.
 */

public class Record {
    private String sampleID;
    private String recordSubmitTime;
    private String sampleHolderNo;
    private String sampleDuration;
    private String expID;
    private String expName;
    private String expDescr;
    private String userID;
    private String groupAbbr;
    private String solventAbbr;
    private String spectrometerID;
    private String spectrometerName;
    private String spectrometerCapacity;


    public Record(String sampleID, String recordSubmitTime, String sampleHolderNo, String sampleDuration, String expID,
                  String expName, String expDescr, String userID, String groupAbbr, String solventAbbr, String spectrometerID,
                  String spectrometerName, String spectrometerCapacity){
        this.sampleID=sampleID;
        this.recordSubmitTime=recordSubmitTime;
        this.sampleHolderNo=sampleHolderNo;
        this.sampleDuration=sampleDuration;
        this.expID=expID;
        this.expName=expName;
        this.expDescr=expDescr;
        this.userID=userID;
        this.groupAbbr=groupAbbr;
        this.solventAbbr=solventAbbr;
        this.spectrometerID=spectrometerID;
        this.spectrometerName=spectrometerName;
        this.spectrometerCapacity=spectrometerCapacity;
    }

    public Record(){

    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getRecordSubmitTime() {
        return recordSubmitTime;
    }

    public void setRecordSubmitTime(String recordSubmitTime) {
        this.recordSubmitTime = recordSubmitTime;
    }

    public String getSampleHolderNo() {
        return sampleHolderNo;
    }

    public void setSampleHolderNo(String sampleHolderNo) {
        this.sampleHolderNo = sampleHolderNo;
    }

    public String getSampleDuration() {
        return sampleDuration;
    }

    public void setSampleDuration(String sampleDuration) {
        this.sampleDuration = sampleDuration;
    }

    public String getExpID() {
        return expID;
    }

    public void setExpID(String expID) {
        this.expID = expID;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpDescr() {
        return expDescr;
    }

    public void setExpDescr(String expDescr) {
        this.expDescr = expDescr;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGroupAbbr() {
        return groupAbbr;
    }

    public void setGroupAbbr(String groupAbbr) {
        this.groupAbbr = groupAbbr;
    }

    public String getSolventAbbr() {
        return solventAbbr;
    }

    public void setSolventAbbr(String solventAbbr) {
        this.solventAbbr = solventAbbr;
    }

    public String getSpectrometerID() {
        return spectrometerID;
    }

    public void setSpectrometerID(String spectrometerID) {
        this.spectrometerID = spectrometerID;
    }

    public String getSpectrometerName() {
        return spectrometerName;
    }

    public void setSpectrometerName(String spectrometerName) {
        this.spectrometerName = spectrometerName;
    }

    public String getSpectrometerCapacity() {
        return spectrometerCapacity;
    }

    public void setSpectrometerCapacity(String spectrometerCapacity) {
        this.spectrometerCapacity = spectrometerCapacity;
    }
}
