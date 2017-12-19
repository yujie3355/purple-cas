package purple.cas.mapper;

import purple.cas.model.AuthToken;

public interface AuthTokenMapper {
    int deleteByPrimaryKey(String accessToken);

    int insert(AuthToken record);

    int insertSelective(AuthToken record);

    AuthToken selectByPrimaryKey(String accessToken);

    int updateByPrimaryKeySelective(AuthToken record);

    int updateByPrimaryKey(AuthToken record);
}