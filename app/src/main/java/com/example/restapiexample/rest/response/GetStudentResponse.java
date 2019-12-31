package com.example.restapiexample.rest.response;

import androidx.annotation.NonNull;

import com.example.restapiexample.rest.data.Student;

import java.util.List;

public class GetStudentResponse extends BaseResponse {
    private List<Student> students;

    public GetStudentResponse() {
        super(200);
    }

    public GetStudentResponse(List<Student> students) {
        this();
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("students : [");
        for (int i = 0; i < students.size(); i++) {
            sb.append(students.get(i).toString());
            if (i < (students.size() - 1)) {
                sb.append(",\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
