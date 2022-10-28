import styled from "styled-components";

import "App.css";

const Main = styled.div`
  width: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: SCDream5;

  @media screen and (max-width: 768px) {
    width: 90%;
    margin: 30px auto;
    height: 300px;
  }
`;

const Content = styled.div`
  height: 300px;
  border: 3px solid gray;
  border-radius: 15px;

  > div {
    padding: 0px 5px;
  }
`;

const Result4CEFRExplain = () => {
  return (
    <Main>
      <Content>
        <div>
          CEFR이란 유럽연합 공통언어 표준등급으로 언어 능력을 설명해주는
          국제적으로 공인된 표준입니다.
        </div>
        <br />
        <div>
          현재 홍길동 학생의 CEFR 점수의 경우 국가공인자격 예상 시험 점수는
          다음과 같습니다. TOEIC : ~~~~ 점 TOEFL : ~~~~ 점
        </div>
        <br />
        <div>홍길동 학생의 전국 백분위는 80%입니다.</div>
      </Content>
    </Main>
  );
};

export default Result4CEFRExplain;
