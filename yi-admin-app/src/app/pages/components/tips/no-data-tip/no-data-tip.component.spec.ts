import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NoDataTipComponent} from './no-data-tip.component';

describe('NoDataTipComponent', () => {
    let component: NoDataTipComponent;
    let fixture: ComponentFixture<NoDataTipComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [NoDataTipComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(NoDataTipComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
