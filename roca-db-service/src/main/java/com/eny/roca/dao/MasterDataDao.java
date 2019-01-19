package com.eny.roca.dao;

import java.util.List;

import com.eny.roca.db.bean.MasterData;

public interface MasterDataDao {

	List<MasterData> getRoles(String string);

	List<MasterData> getCountryData();

	List<MasterData> getCityDataByCountry(Integer countryId);

	List<IndustryMasterData> getIndustry();

	List<MasterData> getRocaTeamMemners(String roleName);

	List<Object> getIndustryName(String Id);

	Integer getJrMemberQueryAlloacted(String jrMember);

 

}
