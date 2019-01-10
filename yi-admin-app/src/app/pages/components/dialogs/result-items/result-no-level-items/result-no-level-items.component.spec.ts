import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ResultNoLevelItemsComponent} from './result-no-level-items.component';

describe('ResultNoLevelItemsComponent', () => {
    let component: ResultNoLevelItemsComponent;
    let fixture: ComponentFixture<ResultNoLevelItemsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ResultNoLevelItemsComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ResultNoLevelItemsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
