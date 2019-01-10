import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ObjectLogComponent} from './object-log.component';

describe('ObjectLogComponent', () => {
    let component: ObjectLogComponent;
    let fixture: ComponentFixture<ObjectLogComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ObjectLogComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ObjectLogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
