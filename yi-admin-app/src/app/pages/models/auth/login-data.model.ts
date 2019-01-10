import {UserSimple} from '../domain/simple/user-simple.model';
import {DeptSimple} from '../domain/simple/dept-simple.model';
import {Result} from '../enums/result.enum';
import {SessionData} from "./session-data.model";

/**
 *  用户登录后的数据
 *  对应后端的 com.geosun.yi.web.auth.service.RestLoginResult
 */
export class LoginData {
    token: string;  // JWT令牌
    sessionData: SessionData;  // 会话信息
    user: UserSimple;  // 当前员工
    workDepts: Array<DeptSimple>;  // 所属部门
    data: any;
    result: Result;
    message: string;
}
