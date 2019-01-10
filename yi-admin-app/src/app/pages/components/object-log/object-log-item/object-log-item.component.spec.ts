import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ObjectLogItemComponent} from './object-log-item.component';

describe('ObjectLogItemComponent', () => {
    let component: ObjectLogItemComponent;
    let fixture: ComponentFixture<ObjectLogItemComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ObjectLogItemComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ObjectLogItemComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
