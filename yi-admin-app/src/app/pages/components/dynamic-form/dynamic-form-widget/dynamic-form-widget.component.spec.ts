import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetComponent} from './dynamic-form-widget.component';

describe('DynamicFormWidgetComponent', () => {
    let component: DynamicFormWidgetComponent;
    let fixture: ComponentFixture<DynamicFormWidgetComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
