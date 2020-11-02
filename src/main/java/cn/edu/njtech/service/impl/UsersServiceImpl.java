package cn.edu.njtech.service.impl;

import cn.edu.njtech.domain.dao.DepartmentAdmin;
import cn.edu.njtech.domain.dao.SchoolAdmin;
import cn.edu.njtech.domain.dao.Student;
import cn.edu.njtech.domain.dao.Teacher;
import cn.edu.njtech.mapper.DepartmentAdminMapper;
import cn.edu.njtech.mapper.SchoolAdminMapper;
import cn.edu.njtech.mapper.StudentMapper;
import cn.edu.njtech.mapper.TeacherMapper;
import cn.edu.njtech.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    SchoolAdminMapper schoolAdminMapper;

    @Resource
    DepartmentAdminMapper departmentAdminMapper;

    @Resource
    TeacherMapper teacherMapper;

    @Resource
    StudentMapper studentMapper;

    /**
     *
     * @param status 身份
     * @param academy 学院
     * @param department 专业/部门
     * @param myLimit 此用户的权限
     * @return LinkedList集合
     */
    @Override
    public LinkedList queryUsers(Byte status, String academy, String department, Byte myLimit) {
        List list = new LinkedList();
        switch (status) {
            case -1:
                switch (myLimit) {
                    case 1:
                        list.addAll(this.queryUsersByStatus((byte) 1, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 2, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    case 2:
                        list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    case 3:
                        list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                list.addAll(this.queryUsersByStatus((byte) 1, academy, department));
                break;
            case 2:
                list.addAll(this.queryUsersByStatus((byte) 2, academy, department));
                break;
            case 3:
                list.addAll(this.queryUsersByStatus((byte) 3, academy, department));
                break;
            case 4:
                list.addAll(this.queryUsersByStatus((byte) 4, academy, department));
                break;
            default:
                break;
        }
        return (LinkedList) list;
    }


    /**
     * @author 刘成
     * @time 2020/10/28
     * @param status 身份
     * @param academy 学院
     * @param department 专业/部门
     * @return
     * 用来查询所在学院 专业/部门 的某身份的用户，返回一个LinkedList集合
     */
    public LinkedList queryUsersByStatus(Byte status, String academy, String department) {
        SchoolAdmin schoolAdmin = null;
        DepartmentAdmin departmentAdmin = null;
        Teacher teacher = null;
        Student student = null;
        List list = new LinkedList();
        if (status == 1) {
            schoolAdmin = new SchoolAdmin();
            if (!"-1".equals(academy)) {
                schoolAdmin.setAcademy(academy);
            }
            if (!"-1".equals(department)) {
                schoolAdmin.setDepartment(academy);
            }
            list = schoolAdminMapper.selectUsersSlective(schoolAdmin);
        } else if (status == 2) {
            departmentAdmin = new DepartmentAdmin();
            if (!"-1".equals(academy)) {
                departmentAdmin.setAcademy(academy);
            }
            if (!"-1".equals(department)) {
                departmentAdmin.setDepartment(department);
            }
            list = departmentAdminMapper.selectUsersSlective(departmentAdmin);

        } else if (status == 3) {
            teacher = new Teacher();
            if (!"-1".equals(academy)) {
                teacher.setAcademy(academy);
            }
            if (!"-1".equals(department)) {
                teacher.setDepartment(department);
            }
            list = teacherMapper.selectUsersSlective(teacher);
        } else if (status == 4) {
            student = new Student();
            if (!"-1".equals(academy)) {
                student.setAcademy(academy);
            }
            if (!"-1".equals(department)) {
                student.setDepartment(department);
            }
            list = studentMapper.selectUsersSlective(student);
        }
        return (LinkedList) list;
    }
}
