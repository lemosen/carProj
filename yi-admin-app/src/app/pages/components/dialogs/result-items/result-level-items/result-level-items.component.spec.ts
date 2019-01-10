import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ResultLevelItemsComponent} from './result-level-items.component';

describe('ResultLevelItemsComponent', () => {
    let component: ResultLevelItemsComponent;
    let fixture: ComponentFixture<ResultLevelItemsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ResultLevelItemsComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ResultLevelItemsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
