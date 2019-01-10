import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetTextComponent} from './dynamic-form-widget-text.component';

describe('DynamicFormWidgetTextComponent', () => {
    let component: DynamicFormWidgetTextComponent;
    let fixture: ComponentFixture<DynamicFormWidgetTextComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetTextComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetTextComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
