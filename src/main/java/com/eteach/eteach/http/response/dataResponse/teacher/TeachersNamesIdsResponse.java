package com.eteach.eteach.http.response.dataResponse.teacher;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.teacher.utils.TeacherNameWithId;
import com.eteach.eteach.model.account.TeacherAccount;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class TeachersNamesIdsResponse extends ApiResponse {

    private List<TeacherNameWithId> data;


    public TeachersNamesIdsResponse(){
        data = new ArrayList<>();
    }

    public TeachersNamesIdsResponse(HttpStatus status, String message, List<TeacherNameWithId> teacherNameWithIds){
        super(status, message);
        this.data = teacherNameWithIds;
    }

    public List<TeacherNameWithId> getData() {
        return data;
    }

    public void setData(List<TeacherNameWithId> data) {
        this.data = data;
    }
}
