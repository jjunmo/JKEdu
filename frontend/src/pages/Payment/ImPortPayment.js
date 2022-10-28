import styled from "styled-components";

import "App.css";

const PaymentButton = styled.button`
  box-sizing: border-box;
  border: none;
  border-radius: 1rem;
  max-width: 15rem;
  width: 15rem;
  aspect-ratio: 16 / 6;

  font-family: SCDream5;
  font-size: 2rem;
  background-color: #fd9f28;

  cursor: pointer;

  @media screen and (max-width: 768px) {
    font-size: 1rem;
    width: 80%;
  }
`;

const ImPortPayment = (prop) => {
  const payInfo = prop.payInfo;

  // 아임포트 결제 연동 함수
  const paymenting = () => {
    // 1. 가맹점 식별
    var IMP = window.IMP;
    IMP.init("imp14410218");

    // 2. 결제 데이터 정의
    const data = {
      pg: "html5_inicis", // PG사
      pay_method: "card", // 결제수단
      merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
      amount: payInfo.price, // 결제금액
      name: payInfo.productName, // 주문명
      buyer_name: "홍길동", // 구매자 이름
      buyer_tel: "01012345678", // 구매자 전화번호
      buyer_email: "example@example", // 구매자 이메일
      buyer_addr: "부산 661-16", // 구매자 주소
      buyer_postcode: "06018", // 구매자 우편번호

      // 아래코드에 redirect될 url을 작성하면 된다.
      // m_redirect_url: "172.30.1.8:3000/payment/trying",
      m_redirect_url: "localhost:3000/payment/trying",
    };

    // 4. 콜백 함수(모바일에서는 작동 x)
    const callback = (response) => {
      const { success, merchant_uid, error_msg } = response;
      console.log(response);
      if (success) {
        alert("결제 성공");
      } else {
        alert(`결제 실패: ${error_msg}`);
      }
    };

    // 3. 결제 창 호출
    IMP.request_pay(data, callback);
  };

  return <PaymentButton onClick={paymenting}>결제하기</PaymentButton>;
};

export default ImPortPayment;
