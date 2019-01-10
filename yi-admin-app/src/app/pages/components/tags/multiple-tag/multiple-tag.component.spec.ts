import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MultipleTagComponent} from './multiple-tag.component';

describe('MultipleTagComponent', () => {
    let component: MultipleTagComponent;
    let fixture: ComponentFixture<MultipleTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MultipleTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MultipleTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
