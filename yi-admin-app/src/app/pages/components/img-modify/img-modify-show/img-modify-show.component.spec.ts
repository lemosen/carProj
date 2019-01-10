import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ImgModifyShowComponent} from './img-modify-show.component';

describe('ImgModifyShowComponent', () => {
    let component: ImgModifyShowComponent;
    let fixture: ComponentFixture<ImgModifyShowComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ImgModifyShowComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ImgModifyShowComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
