package top.sharex.oes.dao;

import top.sharex.oes.entity.ExamResult;

public interface ExamResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    int insert(ExamResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    int insertSelective(ExamResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    ExamResult selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExamResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExamResult record);
}