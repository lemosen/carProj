import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetRadioComponent} from './dynamic-form-widget-radio.component';

describe('DynamicFormWidgetRadioComponent', () => {
    let component: DynamicFormWidgetRadioComponent;
    let fixture: ComponentFixture<DynamicFormWidgetRadioComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetRadioComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetRadioComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
