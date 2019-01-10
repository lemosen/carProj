import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserMultipleTagComponent} from './user-multiple-tag.component';

describe('UserMultipleTagComponent', () => {
    let component: UserMultipleTagComponent;
    let fixture: ComponentFixture<UserMultipleTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserMultipleTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UserMultipleTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
