/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VolleyTestModule } from '../../../test.module';
import { LetterDeleteDialogComponent } from 'app/entities/letter/letter-delete-dialog.component';
import { LetterService } from 'app/entities/letter/letter.service';

describe('Component Tests', () => {
    describe('Letter Management Delete Component', () => {
        let comp: LetterDeleteDialogComponent;
        let fixture: ComponentFixture<LetterDeleteDialogComponent>;
        let service: LetterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterDeleteDialogComponent]
            })
                .overrideTemplate(LetterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LetterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LetterService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
