import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetTextAreaComponent} from './dynamic-form-widget-text-area.component';

describe('DynamicFormWidgetTextAreaComponent', () => {
    let component: DynamicFormWidgetTextAreaComponent;
    let fixture: ComponentFixture<DynamicFormWidgetTextAreaComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetTextAreaComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetTextAreaComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
