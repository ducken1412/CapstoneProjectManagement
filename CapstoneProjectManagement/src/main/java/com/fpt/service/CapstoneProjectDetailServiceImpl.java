package com.fpt.service;

import java.util.ArrayList;
import java.util.List;

import com.fpt.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.repository.CapstoneProjectDetailRepository;

@Service
public class CapstoneProjectDetailServiceImpl implements CapstoneProjectDetailService {

    @Autowired
    private CapstoneProjectDetailRepository capstoneProjectDetailRepository;

    @Override
    public boolean addCapstonprojectDetail(CapstoneProjectDetails capstoneProjectDetails) {
        try {
            capstoneProjectDetailRepository.save(capstoneProjectDetails);
            return true;
        } catch (Exception e) {
            System.out.println("eror add capstone project detail");
        }
        return false;
    }

    @Override
    public List<CapstoneProjectDetails> getDetailByCapstoneProjectId(Integer id) {
        return capstoneProjectDetailRepository.getDetailByCapstoneProjectId(id);
    }

    @Override
    public List<CapstoneProjectDetails> getUserByCapstioneID(Integer id) {
        return capstoneProjectDetailRepository.getUserIdByCapstoneProjectDetailId(id);
        //return new ArrayList<>();
    }

    @Override
    public List<Integer> getProjectIdByUserId(String id) {
        return capstoneProjectDetailRepository.getIdProjectByUserID(id);

    }
    @Override
    public Integer getStatuByCapstoneProjectDetailIdAndUserId(Integer cpId, Integer userId) {
        return capstoneProjectDetailRepository.getStatuByCapstoneProjectDetailIdAndUserId(cpId, userId);
    }

    @Override
    public List<Users> getUserByCapstoneProjectDetailId(Integer id) {
        return capstoneProjectDetailRepository.getUserByCapstoneProjectDetailId(id);
    }

    @Override

    public Integer countLecturersByProjectId(Integer id) {
        return capstoneProjectDetailRepository.countLecturersByCapstoneProjectId(id);
    }

    @Override
    public Integer updateStatusUserProject(String uid, Integer pid) {
        return capstoneProjectDetailRepository.updateStatusUserProject(uid,pid);
    }


    public Users findUserByStatusRegisted(String id) {
        return capstoneProjectDetailRepository.findUserByStatusRegisted(id);
    }

    @Override
    public Integer deleteCapstoneProjectDetailsByUserId(String uid, Integer pid) {
        return capstoneProjectDetailRepository.deleteCapstoneProjectDetailsByUserId(uid,pid);
    }

    @Override
    public Integer deleteRejectCapstoneProjectDetailsByUserId(String uid, Integer pid) {
        return capstoneProjectDetailRepository.deleteRejectCapstoneProjectDetailsByUserId(uid, pid);
    }

    @Override
	public List<Integer> getIdProjectByUserIDCheckApprove(String id) {
		// TODO Auto-generated method stub
		return capstoneProjectDetailRepository.getIdProjectByUserIDCheckApprove(id);
	}

//    @Override
//    public Integer deleteCapstoneProjectDetailsByUserId(String id) {
//        return capstoneProjectDetailRepository.deleteCapstoneProjectDetailsByUserId(id);
//    }

}
