import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetSelectComponent} from './dynamic-form-widget-select.component';

describe('DynamicFormWidgetSelectComponent', () => {
    let component: DynamicFormWidgetSelectComponent;
    let fixture: ComponentFixture<DynamicFormWidgetSelectComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetSelectComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetSelectComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
