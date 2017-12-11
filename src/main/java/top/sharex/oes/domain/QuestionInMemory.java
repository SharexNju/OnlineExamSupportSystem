package top.sharex.oes.domain;

import java.util.List;

/**
 * @author Crystal
 * @Date 2017/10/30 15:42
 */
public class QuestionInMemory {
    private Integer questionId;
    /**
     * 试题内容
     */
    private String content;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 类型
     */
    private Byte type;
    /**
     * 选项
     */
    private List<Choice> choices;
    /**
     * 是否已作答
     */
    private Boolean isAnswered;
    /**
     * 是否被做标记
     */
    private Boolean isMarked;

    public QuestionInMemory() {
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public Boolean getMarked() {
        return isMarked;
    }

    public void setMarked(Boolean marked) {
        isMarked = marked;
    }
}
