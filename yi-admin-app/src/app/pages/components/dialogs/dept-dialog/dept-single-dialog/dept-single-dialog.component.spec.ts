import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptSingleDialogComponent} from './dept-single-dialog.component';

describe('DeptSingleDialogComponent', () => {
    let component: DeptSingleDialogComponent;
    let fixture: ComponentFixture<DeptSingleDialogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptSingleDialogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptSingleDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
