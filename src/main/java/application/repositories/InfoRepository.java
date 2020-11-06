package application.repositories;

import application.entities.Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InfoRepository {

    @Select("select * from tbl_info where id = #{id}")
    Info getInfoById(Long id);

    @Select("select * from tbl_info where name = #{name}")
    Info getInfoByName(String name);

    @Insert("insert into tbl_info (name, value) values (#{name}, #{value})")
    void saveInfo(Info info);

    @Update("update tbl_info set value = #{value}, name = #{name} where id = #{id}")
    void updateInfo(Info info);
}
