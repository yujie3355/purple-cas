package purple.cas.mapper;

import purple.cas.model.AuthCode;

public interface AuthCodeMapper {

    int deleteByPrimaryKey(String authCode);

    int insert(AuthCode record);

    int insertSelective(AuthCode record);

    AuthCode selectByPrimaryKey(String authCode);

    int updateByPrimaryKeySelective(AuthCode record);

    int updateByPrimaryKey(AuthCode record);
}