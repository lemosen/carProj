import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-img-modify-show',
    templateUrl: './img-modify-show.component.html',
    styleUrls: ['./img-modify-show.component.scss']
})
export class ImgModifyShowComponent implements OnInit {

    @Input() sourceImg: string;

    @Input() sourceWidth: string;

    constructor() {
    }

    ngOnInit() {
    }

}
