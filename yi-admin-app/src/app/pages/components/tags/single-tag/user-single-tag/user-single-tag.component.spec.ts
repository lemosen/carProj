import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserSingleTagComponent} from './user-single-tag.component';

describe('UserSingleTagComponent', () => {
    let component: UserSingleTagComponent;
    let fixture: ComponentFixture<UserSingleTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserSingleTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UserSingleTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
