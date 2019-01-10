import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetChooseTagComponent} from './dynamic-form-widget-choose-tag.component';

describe('DynamicFormWidgetChooseTagComponent', () => {
    let component: DynamicFormWidgetChooseTagComponent;
    let fixture: ComponentFixture<DynamicFormWidgetChooseTagComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetChooseTagComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetChooseTagComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
