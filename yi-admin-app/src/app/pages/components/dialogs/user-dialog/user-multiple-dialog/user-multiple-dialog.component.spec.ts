import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserMultipleDialogComponent} from './user-multiple-dialog.component';

describe('UserMultipleDialogComponent', () => {
    let component: UserMultipleDialogComponent;
    let fixture: ComponentFixture<UserMultipleDialogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserMultipleDialogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UserMultipleDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
