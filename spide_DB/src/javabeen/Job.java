package javabeen;

public class Job {
	private String position;//职位
	private String company;//公司名
	private String compensation;//薪资
	private String workplace;//工作地点
	private String date;//发布日期;
	private String education;//学历
	private String experience;//工作经验
	private String type;//职位类型
	private String number;//工作人数
	private String jobdescription;//职位描述
	private String comdescription;//公司描述
	
	
	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Job(String position, String company, String compensation, String workplace, String date, String education,
			String experience, String type, String number, String jobdescription, String comdescription) {
		super();
		this.position = position;
		this.company = company;
		this.compensation = compensation;
		this.workplace = workplace;
		this.date = date;
		this.education = education;
		this.experience = experience;
		this.type = type;
		this.number = number;
		this.jobdescription = jobdescription;
		this.comdescription = comdescription;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompensation() {
		return compensation;
	}
	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getJobdescription() {
		return jobdescription;
	}
	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}
	public String getComdescription() {
		return comdescription;
	}
	public void setComdescription(String comdescription) {
		this.comdescription = comdescription;
	}
	@Override
	public String toString() {
		return "Job [position=" + position + ", company=" + company + ", compensation=" + compensation + ", workplace="
				+ workplace + ", date=" + date + ", education=" + education + ", experience=" + experience + ", type="
				+ type + ", number=" + number + ", jobdescription=" + jobdescription + ", comdescription="
				+ comdescription + "]";
	}
	
}
