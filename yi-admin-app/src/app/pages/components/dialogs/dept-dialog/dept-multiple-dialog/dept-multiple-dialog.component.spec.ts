import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptMultipleDialogComponent} from './dept-multiple-dialog.component';

describe('DeptMultipleDialogComponent', () => {
    let component: DeptMultipleDialogComponent;
    let fixture: ComponentFixture<DeptMultipleDialogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptMultipleDialogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptMultipleDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
