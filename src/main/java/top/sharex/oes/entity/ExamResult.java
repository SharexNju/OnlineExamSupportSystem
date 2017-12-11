package top.sharex.oes.entity;

public class ExamResult {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_result.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_result.exam_id
     *
     * @mbggenerated
     */
    private Integer examId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_result.student_id
     *
     * @mbggenerated
     */
    private Integer studentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_result.score
     *
     * @mbggenerated
     */
    private Integer score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_result.exam_text
     *
     * @mbggenerated
     */
    private String examText;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    public ExamResult(Integer id, Integer examId, Integer studentId, Integer score, String examText) {
        this.id = id;
        this.examId = examId;
        this.studentId = studentId;
        this.score = score;
        this.examText = examText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    public ExamResult() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_result.id
     *
     * @return the value of exam_result.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_result.id
     *
     * @param id the value for exam_result.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_result.exam_id
     *
     * @return the value of exam_result.exam_id
     *
     * @mbggenerated
     */
    public Integer getExamId() {
        return examId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_result.exam_id
     *
     * @param examId the value for exam_result.exam_id
     *
     * @mbggenerated
     */
    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_result.student_id
     *
     * @return the value of exam_result.student_id
     *
     * @mbggenerated
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_result.student_id
     *
     * @param studentId the value for exam_result.student_id
     *
     * @mbggenerated
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_result.score
     *
     * @return the value of exam_result.score
     *
     * @mbggenerated
     */
    public Integer getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_result.score
     *
     * @param score the value for exam_result.score
     *
     * @mbggenerated
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_result.exam_text
     *
     * @return the value of exam_result.exam_text
     *
     * @mbggenerated
     */
    public String getExamText() {
        return examText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_result.exam_text
     *
     * @param examText the value for exam_result.exam_text
     *
     * @mbggenerated
     */
    public void setExamText(String examText) {
        this.examText = examText == null ? null : examText.trim();
    }
}