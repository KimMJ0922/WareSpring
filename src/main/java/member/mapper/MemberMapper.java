package member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import member.dto.MemberDTO;

@Repository
@Mapper
public interface MemberMapper {
	public MemberDTO login(String email);
}
