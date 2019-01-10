import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppConfig} from "../../configs/app-config";
import {BaseService} from "../../../services/base.service";

@Injectable()
export class CommentService extends BaseService {

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "comment");
    }

    reply(id,replyContent){
        return this.getByParams("reply",{id:id,replyContent:replyContent})
    }

    updateDispaly(id,display){
        return this.getByParams("updateDispaly",{id:id,display:display})
    }


}
