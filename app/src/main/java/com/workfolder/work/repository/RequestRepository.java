package com.workfolder.work.repository;

import com.workfolder.work.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByStudentIdAndStatus(int studentId, Request.RequestStatus status);
    List<Request> findByTeacherIdAndStatus(int teacherId, Request.RequestStatus status);
    List<Request> findByStudentId(int studentId);

}
