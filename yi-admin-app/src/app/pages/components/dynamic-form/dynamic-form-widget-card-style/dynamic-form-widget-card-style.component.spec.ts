import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DynamicFormWidgetCardStyleComponent} from './dynamic-form-widget-card-style.component';

describe('DynamicFormWidgetCardStyleComponent', () => {
    let component: DynamicFormWidgetCardStyleComponent;
    let fixture: ComponentFixture<DynamicFormWidgetCardStyleComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DynamicFormWidgetCardStyleComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DynamicFormWidgetCardStyleComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
