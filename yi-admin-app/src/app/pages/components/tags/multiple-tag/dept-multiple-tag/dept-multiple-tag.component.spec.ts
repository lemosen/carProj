import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptMultipleTagComponent} from './dept-multiple-tag.component';

describe('DeptMultipleTagComponent', () => {
    let component: DeptMultipleTagComponent;
    let fixture: ComponentFixture<DeptMultipleTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptMultipleTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptMultipleTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
