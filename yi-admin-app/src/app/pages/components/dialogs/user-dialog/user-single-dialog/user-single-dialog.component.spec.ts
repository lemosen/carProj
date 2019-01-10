import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserSingleDialogComponent} from './user-single-dialog.component';

describe('UserSingleDialogComponent', () => {
    let component: UserSingleDialogComponent;
    let fixture: ComponentFixture<UserSingleDialogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserSingleDialogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UserSingleDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
