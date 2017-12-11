package top.sharex.oes.domain;

import java.util.List;

/**
 * @author danielyang
 * @Date 2017/10/30 15:42
 */
public class Exam {
    private Integer examId;
    /**
     * 考试标题
     */
    private String title;
    /**
     * 考试列表
     */
    private List<QuestionInMemory> questionInMemoryList;

    private Integer studentId;

    public Exam() {
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionInMemory> getQuestionInMemoryList() {
        return questionInMemoryList;
    }

    public void setQuestionInMemoryList(List<QuestionInMemory> questionInMemoryList) {
        this.questionInMemoryList = questionInMemoryList;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
