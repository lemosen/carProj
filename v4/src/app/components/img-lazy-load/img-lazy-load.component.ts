import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'img-lazy-load',
    templateUrl: './img-lazy-load.component.html',
    styleUrls: ['./img-lazy-load.component.scss']
})
export class ImgLazyLoadComponent {
    default: string = 'assets/loading.gif';

    @Input() src: string;

    constructor() {
    }

    // ngOnInit() {
    //     let img = new Image();
    //     img.src = this.src;
    //     img.onload = () => {
    //         这里为了达到演示效果给了两秒的延迟，实际使用中不需要延迟
    //         setTimeout(() => {
    // this.default = this.src;
    // }, 3000)
    // }
    // }

    ngOnChanges() {
        let img = new Image();
        img.src = this.src;
        img.onload = () => {
            this.default = this.src;
        };
        img.onerror = () => {
            this.default = 'assets/imgs/null-category.png';
        }
    }

}
