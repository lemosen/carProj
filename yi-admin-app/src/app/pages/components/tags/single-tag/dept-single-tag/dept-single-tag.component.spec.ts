import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptSingleTagComponent} from './dept-single-tag.component';

describe('DeptSingleTagComponent', () => {
    let component: DeptSingleTagComponent;
    let fixture: ComponentFixture<DeptSingleTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptSingleTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptSingleTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
