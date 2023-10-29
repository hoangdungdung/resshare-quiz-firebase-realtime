package com.resshare.springboot;

import com.resshare.book.FirebaseBookServices;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resshare.book.addbook.LoadFormAddBookListener;
import com.resshare.book.bookcase.LoadFormMyBoocaseListener;
import com.resshare.book.bookcase.LoadFormViewMyBookcaseListener;
import com.resshare.book.bookcase.RemoveBookItem;
import com.resshare.book.profile.AddProfileListener;
import com.resshare.book.profile.LoadFormProfileListener;
import com.resshare.book.search.Blacklist;
import com.resshare.book.search.LoadFormSearchingListener;
import com.resshare.book.search.MessageRequestBookLoadFormListener;
import com.resshare.book.search.ObjectionableContent;
import com.resshare.book.search.RatingBook;
import com.resshare.book.search.ReportUser;
import com.resshare.book.search.SeclectBookService;
import com.resshare.book.transaction.BookSupplierAcceptOrderListener;
import com.resshare.quiz.services.AddSubjectListener;
import com.resshare.quiz.services.AnswerListener;
import com.resshare.quiz.services.CommitTotalAnswerListener;
import com.resshare.quiz.services.ImportQuessionListener;
import com.resshare.quiz.services.LoadGetListQuestionListener;
import com.resshare.quiz.services.grade.DashboardGradeCfgListener;
import com.resshare.quiz.services.grade.FormOptionCfgGradeListener;
import com.resshare.quiz.services.grade.LoadFormQuizGeneralListener;
import com.resshare.quiz.services.topic.LoadFormQuizTopicGeneralListener;

@SpringBootApplication // (scanBasePackages = { "com.websystique.springboot" }) // same as
						// @Configuration
						// @EnableAutoConfiguration @ComponentScan
						// combined
public class ServiceListenerQuizStart {

	public static void startListener() {
//
//		FirebaseBookServices firebaseServices = new FirebaseBookServices();
//		firebaseServices.onStart();
//
//
//		LoadFormAddBookListener loadFormAddBookListener = new LoadFormAddBookListener();
//		loadFormAddBookListener.onStart();
//
//		LoadFormSearchingListener loadFormSearchingListener = new LoadFormSearchingListener();
//		loadFormSearchingListener.onStart();
//
//		SeclectBookService seclectBookService = new SeclectBookService();
//		seclectBookService.onStart();
//
//		BookSupplierAcceptOrderListener supplierAcceptOrderListener = new BookSupplierAcceptOrderListener();
//		supplierAcceptOrderListener.onStart();
//
//		LoadFormMyBoocaseListener loadFormMyBoocaseListener = new LoadFormMyBoocaseListener();
//		loadFormMyBoocaseListener.onStart();
//
//		LoadFormViewMyBookcaseListener loadFormViewMyBookcaseListener = new LoadFormViewMyBookcaseListener();
//		loadFormViewMyBookcaseListener.onStart();
//
//		LoadFormProfileListener loadFormSupplierOxyListener = new LoadFormProfileListener();
//		loadFormSupplierOxyListener.onStart();
//
//		AddProfileListener addProfileListener = new AddProfileListener();
//		addProfileListener.onStart();
//
//		MessageRequestBookLoadFormListener messageRequestBookLoadFormListener = new MessageRequestBookLoadFormListener();
//		messageRequestBookLoadFormListener.onStart();
//
//		RemoveBookItem removeBookItem = new RemoveBookItem();
//		removeBookItem.onStart();
//
//		RatingBook ratingBook = new RatingBook();
//		ratingBook.onStart();
//
//		Blacklist blacklist = new Blacklist();
//		blacklist.onStart();
//
//		ObjectionableContent objectionableContent = new ObjectionableContent();
//		objectionableContent.onStart();
//
//		ReportUser reportUser = new ReportUser();
//		reportUser.onStart();

		LoadFormQuizGeneralListener loadFormInformaticsListener = new LoadFormQuizGeneralListener();
		loadFormInformaticsListener.onStart();
		LoadGetListQuestionListener loadFormInformaticsListener1 = new LoadGetListQuestionListener();
		loadFormInformaticsListener1.onStart();

		AnswerListener loadFormInformaticsListener2 = new AnswerListener();
		loadFormInformaticsListener2.onStart();

		CommitTotalAnswerListener loadFormInformaticsListener3 = new CommitTotalAnswerListener();
		loadFormInformaticsListener3.onStart();

		DashboardGradeCfgListener loadFormInformaticsListenerGradeCfg = new DashboardGradeCfgListener();
		loadFormInformaticsListenerGradeCfg.onStart();
		FormOptionCfgGradeListener loadFormInformaticsListenerGradeCfg2 = new FormOptionCfgGradeListener();
		loadFormInformaticsListenerGradeCfg2.onStart();

		LoadFormQuizTopicGeneralListener loadFormQuizTopicGeneralListener = new LoadFormQuizTopicGeneralListener();
		loadFormQuizTopicGeneralListener.onStart();

		ImportQuessionListener importQuessionListener = new ImportQuessionListener();
		importQuessionListener.onStart();

		AddSubjectListener addSubjectListener = new AddSubjectListener();
		addSubjectListener.onStart();


	}

}
