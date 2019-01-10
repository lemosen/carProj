import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StorageVolumePage} from './storage-volume.page';

describe('StorageVolumePage', () => {
    let component: StorageVolumePage;
    let fixture: ComponentFixture<StorageVolumePage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [StorageVolumePage],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(StorageVolumePage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
