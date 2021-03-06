package com.example.user.interview;

public class Problem {
	
	private int problemid;
	private int type;
	private String name;
	private String content;
	private String answer;
	private int paperid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProblemid() {
		return problemid;
	}
	public void setProblemid(int problemid) {
		this.problemid = problemid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getPaperid() {
		return paperid;
	}
	public void setPaperid(int paperid) {
		this.paperid = paperid;
	}

	@Override
	public String toString() {
		return "Problem{" +
				"problemid=" + problemid +
				", type=" + type +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", answer='" + answer + '\'' +
				", paperid=" + paperid +
				'}';
	}
}
