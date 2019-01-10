import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {NavController, Slides} from "@ionic/angular";

@Component({
    selector: 'banner-slide',
    templateUrl: './banner-slide.component.html',
    styleUrls: ['./banner-slide.component.scss']
})
export class BannerSlideComponent implements OnChanges {

    @Input()
    banners = [];

    @ViewChild('slides') slides: Slides;

    timer;

    constructor(public navCtrl: NavController,) {
    }

    ngAfterViewInit() {
        this.autoPlay()
    }

    ngOnDestroy() {
        clearTimeout(this.timer);
    }

    autoPlay() {
        let _home = this;

        this.timer = setTimeout(() => {
            if (_home.slides) {
                _home.slides.isEnd().then(data => {
                    if (data) {
                        _home.slides.slideTo(0);
                    } else {
                        _home.slides.slideNext(100);
                    }
                })
            }
            this.autoPlay();
        }, 5000);
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.banners.currentValue) {
            this.banners = changes.banners.currentValue.map(e => {
                return {
                    filePath: e.imgPath ? e.imgPath : e.filePath,
                    linkType: e.linkType,
                    linkId: e.linkId
                }
            })
        }
    }

    goUrl(banner) {
        // 1商品2文章
        if (banner.linkType == 1) {
            window.location.href = './#/tabs/(home:home)';
            setTimeout(() => {
                this.navCtrl.navigateForward(["CommodityPage", {id: banner.linkId}])
            }, 100)
        } else if (banner.linkType == 2) {
            window.location.href = './#/tabs/(home:home)';
            setTimeout(() => {
                this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": banner.linkId}])
            }, 100)
        } else {
            return
        }
    }
}
