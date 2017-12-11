package top.sharex.oes.domain;

/**
 * @author danielyang
 * @Date 2017/10/30 15:50
 */
public class Choice {
    private Integer choiceId;
    /**
     * 内容
     */
    private String content;
    /**
     * 是否正确
     */
    private Boolean isCorrect;
    /**
     * 是否被选择
     */
    private Boolean isChoosed;

    public Choice() {
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Boolean getIsChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(Boolean choosed) {
        isChoosed = choosed;
    }
}
