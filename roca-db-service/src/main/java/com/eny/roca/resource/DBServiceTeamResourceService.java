package com.eny.roca.resource;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eny.roca.dao.MasterDataDao;
import com.eny.roca.db.bean.MasterData;

@RestController
@RequestMapping("/rs/db")
public class DBServiceTeamResourceService {
	@Autowired
	private MasterDataDao masterDataDao;
	
	@GetMapping("/rocajrmembers")
	public List<MasterData> getRocaJuniorTeamMembers(){
		return masterDataDao.getRocaTeamMemners("JUNIOR");	
	}
	
	@GetMapping("/rocasrmembers")
	public List<MasterData> getRocaSeniorTeamMembers(){
		return masterDataDao.getRocaTeamMemners("SENIOR");	
	}
	
	@PostMapping("/jrmemberQueryAlloacted")
	public Integer getJrMemberQueryAlloacted(@RequestBody String jrMember) {
		return masterDataDao.getJrMemberQueryAlloacted(jrMember);
	}
}
